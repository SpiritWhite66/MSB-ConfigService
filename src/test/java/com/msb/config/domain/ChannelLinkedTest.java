package com.msb.config.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.msb.config.web.rest.TestUtil;

public class ChannelLinkedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelLinked.class);
        ChannelLinked channelLinked1 = new ChannelLinked();
        channelLinked1.setId(1L);
        ChannelLinked channelLinked2 = new ChannelLinked();
        channelLinked2.setId(channelLinked1.getId());
        assertThat(channelLinked1).isEqualTo(channelLinked2);
        channelLinked2.setId(2L);
        assertThat(channelLinked1).isNotEqualTo(channelLinked2);
        channelLinked1.setId(null);
        assertThat(channelLinked1).isNotEqualTo(channelLinked2);
    }
}
