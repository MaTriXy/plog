package com.airbnb.plog

import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel

class ByteBufToMessageDecoderTest extends GroovyTestCase {
    void testEmpty() {
        testPayload(new byte[0])
    }

    void testSmall() {
        testPayload('foo'.bytes)
    }

    void testBig() {
        testPayload(('foo' * 1000).bytes)
    }

    private void testPayload(byte[] bytes) {
        final channel = new EmbeddedChannel(new Message.ByteBufToMessageDecoder())
        assert channel.writeInbound(Unpooled.wrappedBuffer(bytes))
        final message = (Message) channel.readInbound()
        assert message.payload == bytes
        assert !channel.finish()
        assert channel.readOutbound() == null
    }
}