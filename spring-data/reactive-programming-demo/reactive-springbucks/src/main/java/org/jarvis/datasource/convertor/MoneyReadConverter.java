package org.jarvis.consumer.convertor;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author tennyson
 * @date 2020/8/8-23:04
 */
@ReadingConverter
public class MoneyReadConverter implements Converter<Long, Money> {
    @Override
    public Money convert(Long source) {
        return Money.ofMajor(CurrencyUnit.of("CNY"), source);
    }
}
