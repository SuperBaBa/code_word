package org.jarvis.consumer.convertor;


import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * @author tennyson
 * @date 2020/8/8-23:01
 */
@WritingConverter
public class MoneyWriteConverter implements Converter<Money, Long> {
    @Override
    public Long convert(Money money) {
        return money.getAmountMajorLong();
    }
}
