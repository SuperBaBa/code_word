package org.jarvis.consumer.processors;

import java.util.ArrayList;

/**
 * author:tennyson date:2020/6/16
 **/
public class ArrayListProcessor extends AbstractProcessor {

    @Override
    public String process(Object obj) {
        ArrayList<String> arrayList= (ArrayList) obj;
        return arrayList.get(arrayList.size()-1);
    }
}
