package org.jarvis.consumer.converter;

import lombok.NonNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * 读取dataBase中Long类型时使用{@link CurrencyUnit}转换成{@link Money}类型
 * @author marcus @date 2020/8/3
 **/
public class MoneyReadConverter implements Converter<Long, Money> {

    /**
     * 转换{@link Long}类型到{@link Money}类型
     *
     * @param aLong 被转换的源对象，不能为null
     * @return
     */
    @Override
    public Money convert(@NonNull Long aLong) {
        return Money.ofMinor(CurrencyUnit.of("CNY"), aLong);
    }
}
