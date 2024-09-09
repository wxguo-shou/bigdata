1.
SELECT
	sku_id
FROM    
(
    SELECT
        sku_id,
        dense_rank() OVER(ORDER BY sum(sku_num) DESC) rn
    FROM order_detail
    GROUP BY sku_id
)t
WHERE rn=2;

2.
SELECT DISTINCT
  (user_id)
FROM
  (
    SELECT
      user_id,
      datediff (create_date, before_one) current_last,
      datediff (before_one, before_two) before_last_two,
      datediff (create_date, before_two) current_last_two
    FROM
      (
        SELECT
          user_id,
          create_date,
          lag (create_date, 2, '1970-01-01') OVER (
            PARTITION BY
              user_id
            ORDER BY
              create_date
          ) before_two,
          lag (create_date, 1, '1970-01-01') OVER (
            PARTITION BY
              user_id
            ORDER BY
              create_date
          ) before_one
        FROM
          order_info
      ) t1
  ) t2
WHERE
  current_last = 1
  AND before_last_two = 1
  AND current_last_two = 2;




3.
select
    t1.category_id,
    t1.category_name,
    sku_id,
    name,
    t1.order_num,
    sku_cnt
from

(SELECT
	detail_two.category_id,
    category_name,
	max(order_num) order_num,
    count(*) sku_cnt
FROM
(
(SELECT
	detail.sku_id sku_id,
    sum(sku_num) order_num,
    name,
    category_id
FROM
(
(SELECT
	sku_id,
    sku_num
FROM order_detail)detail
JOIN
(SELECT
	sku_id,
    name,
    category_id
FROM sku_info)sku
ON detail.sku_id=sku.sku_id
)
GROUP BY detail.sku_id, name,category_id)detail_two
JOIN
(SELECT category_id, category_name FROM category_info)category
ON detail_two.category_id=category.category_id
)
GROUP BY detail_two.category_id,category_name)t1

JOIN

(SELECT
	*
FROM
(
(SELECT
	detail.sku_id sku_id,
    sum(sku_num) order_num,
    name,
    category_id
FROM
(
(SELECT
	sku_id,
    sku_num
FROM order_detail)detail
JOIN
(SELECT
	*
FROM sku_info)sku
ON detail.sku_id=sku.sku_id
)
GROUP BY detail.sku_id, name,category_id)detail_two
JOIN
(SELECT category_id, category_name FROM category_info)category
ON detail_two.category_id=category.category_id
)
)t2
on t1.order_num=t2.order_num;



4.
SELECT
	user_id,
    create_date,
    sum_so_far,
	vip_level
FROM
(
SELECT
	user_id,
    create_date,
    sum_so_far,
	vip_level,
    row_number() OVER(PARTITION BY user_id,create_date ORDER BY sum_so_far DESC) rn
FROM

(
SELECT
	user_id,
    create_date,
    sum_so_far,
    CASE
    	WHEN 0<=sum_so_far AND sum_so_far<10000 THEN "普通会员"
        WHEN 10000<=sum_so_far AND sum_so_far<30000 THEN "青铜会员"
        WHEN 30000<=sum_so_far AND sum_so_far<50000 THEN "白银会员"
        WHEN 50000<=sum_so_far AND sum_so_far<80000 THEN "黄金会员"
        WHEN 80000<=sum_so_far AND sum_so_far<100000 THEN "白金会员"
        WHEN sum_so_far>=100000 THEN "钻石会员"
    END AS vip_level
FROM
(
SELECT
	sum(total_amount) OVER(PARTITION BY user_id ORDER BY create_date ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) sum_so_far,
    user_id,
    create_date
FROM order_info
)t1
)t2
)t3
WHERE rn=1;




5.
SELECT
	concat(round(percentage * 100, 1), "%") percentage
FROM
(SELECT
	order_continuous/total_amount percentage
    
FROM

(SELECT
	count(*) order_continuous
FROM
(SELECT
	DISTINCT (user_id) order_continuous
FROM    

(SELECT
 	user_id,
	datediff(create_date, firstValue) date_diff
FROM
(SELECT
	user_id,
    create_date,
    first_value(create_date, true) OVER(PARTITION BY user_id ORDER BY create_date) firstValue
FROM order_info)t1)t2
WHERE date_diff=1)t3)t5


JOIN

(SELECT 
	count(*) total_amount
FROM
(SELECT
	DISTINCT (user_id)
from order_info
GROUP BY user_id)t4)t6)t7
;



6.
SELECT
	year,
	sum(order_num) order_num,
    sku_id,
    sum(order_amount) order_amount
FROM

(SELECT
	year,
    order_num,
    sku_id,
    order_amount
FROM
(SELECT
	substring(create_date,0,4) year,
    sku_num order_num,
	sku_id,
    sku_num * price order_amount,
    rank() OVER(PARTITION BY sku_id ORDER BY substring(create_date,0,4)) rk
FROM order_detail)t1
WHERE rk=1)t2
GROUP BY sku_id,year;





8.
SELECT
	login_ts login_date_first,
    count(*) user_count
FROM

(SELECT
	user_id,
	login_ts,
    rn
FROM
	
(SELECT
	user_id,
	login_ts,
    row_number() over(PARTITION BY user_id ORDER BY login_ts) rn
FROM

(SELECT
	user_id,
    substring(login_ts, 0, 10) login_ts
    
FROM user_login_detail)t1)t2
WHERE rn=1)t3
GROUP BY login_ts;




9.
SELECT
	sku_id,
    create_date,
    sum_num
FROM
(SELECT
	sku_id,
    create_date,
    sum_num,
    row_number() OVER(PARTITION BY sku_id ORDER BY create_date) rn
FROM

(SELECT
	sku_id,
    create_date,
    sum_num,
    rank() OVER(PARTITION BY sku_id ORDER BY sum_num DESC) rk
FROM

(SELECT
	sku_id,
    sum(sku_num) sum_num,
    create_date
FROM order_detail
GROUP BY sku_id, create_date)t1)t2
WHERE rk=1)t3
WHERE rn=1;




10.
SELECT
	t6.sku_id,
    name,
    cate_avg_num,
    sum_num
FROM

(SELECT
	t4.sku_id sku_id,
    name,
    category_id,
    sum(sku_num) sum_num
FROM
(SELECT
	sku_id,
    name,
    category_id
FROM sku_info)t4
JOIN
(SELECT
	sku_id,
    sku_num
FROM order_detail)t5
ON t4.sku_id=t5.sku_id
GROUP BY t4.sku_id, name, category_id)t6

JOIN

(SELECT
	category_id,
	CAST(sum(sum_num) / count(*) as integer) cate_avg_num
FROM

(SELECT
    t1.sku_id sku_id,
    category_id,
    sum(sku_num)  sum_num
FROM

(SELECT
	sku_id,
    sku_num
FROM order_detail)t1
JOIN
(SELECT
	sku_id,
    category_id
FROM sku_info)t2
ON t1.sku_id=t2.sku_id
GROUP BY t1.sku_id,category_id)t3
GROUP BY category_id)t7
ON t6.category_id=t7.category_id
WHERE sum_num>cate_avg_num;
