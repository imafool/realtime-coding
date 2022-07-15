package com.coco.imports;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.coco.entity.People;

public class ImportHandler extends ExcelDataHandlerDefaultImpl<People> {
    @Override
    public Object importHandler(People obj, String name, Object value) {
        return super.importHandler(obj, name, value);
    }
}
