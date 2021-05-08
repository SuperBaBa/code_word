package org.jarvis.consumer.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.money.Money;

import java.io.IOException;

/**
 * 序列化
 *
 * @author tennyson
 * @date 2020/8/8-22:53
 */
public class MoneySerializer extends StdSerializer<Money> {

    protected MoneySerializer(Class<Money> t) {
        super(t);
    }

    @Override
    public void serialize(Money money, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(money.getAmountMinorLong());
    }
}
