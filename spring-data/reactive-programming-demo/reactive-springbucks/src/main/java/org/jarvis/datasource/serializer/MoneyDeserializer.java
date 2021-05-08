package org.jarvis.consumer.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;

/**
 * 反序列化方法复写
 *
 * @author tennyson
 * @date 2020/8/8-22:58
 */
public class MoneyDeserializer extends StdDeserializer<Money> {

    protected MoneyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Money deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return Money.ofMajor(CurrencyUnit.of("CNY"), jsonParser.getLongValue());
    }
}
