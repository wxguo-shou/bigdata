package com.atguigu.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class MyLen extends GenericUDF{
    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        if(objectInspectors.length!=1){
            throw new UDFArgumentException("只接受一个参数");
        }

        ObjectInspector objectInspector = objectInspectors[0];
        if (ObjectInspector.Category.PRIMITIVE != objectInspector.getCategory()){
            throw new UDFArgumentException("只接受基本数据类型的参数");
        }

        PrimitiveObjectInspector primitiveObjectInspector = (PrimitiveObjectInspector) objectInspector;
        if (primitiveObjectInspector.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
            throw new UDFArgumentException("只接受String类型的参数");
        }

        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        DeferredObject deferredObject = deferredObjects[0];
        Object o = deferredObject.get();

        if (o == null){
            return 0;
        }else {
            return o.toString().length();
        }
    }

    @Override
    public String getDisplayString(String[] strings) {
        return null;
    }
}
