/**
 * ﻿Copyright (C) 2015 - 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * license version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.instagram;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.n52.instagram.decode.JsonUtil;
import org.n52.instagram.decode.MediaDecoder;
import org.n52.instagram.model.PostedImage;
import org.n52.socialmedia.DecodingException;

import static org.hamcrest.CoreMatchers.*;

public class MediaDecoderTest {

	
	@Test
	public void testParsing() throws DecodingException, IOException {
		MediaDecoder decoder = new MediaDecoder();
		List<PostedImage> entries = decoder.parseMediaEntries(JsonUtil.createJson(getClass().getResourceAsStream("media-result.json")));
		
		Assert.assertThat(entries.size(), is(16));
		
		PostedImage first = entries.get(0);
		PostedImage last = entries.get(entries.size()-1);
		
		Assert.assertThat(first.getProcedure().getName(), is("375488012"));
		Assert.assertThat(last.getProcedure().getName(), is("282034696"));
		
		Assert.assertTrue(first.getTags().contains("münster"));
		
		Assert.assertThat(first.getLocation().getLatitude(), is(51.930077892));
		Assert.assertThat(first.getLocation().getLongitude(), is(7.625061267));
		Assert.assertThat(first.getLocation().getName(), is("Preußenstadion"));
		
		Assert.assertThat(first.getLink(), is("http://instagram.com/p/y4NIpoLxb1/"));
		Assert.assertThat(first.getCreatedTime(), is(new DateTime(1423480138000l)));
		Assert.assertThat(first.getImageUrl(), is("http://scontent-b.cdninstagram.com/hphotos-xfp1/t51.2885-15/e15/1738807_817181801669643_1680497657_n.jpg"));
	}
	
	@Test(expected=DecodingException.class)
	public void testReturnStatus() throws DecodingException, IOException {
		MediaDecoder decoder = new MediaDecoder();
		decoder.parseMediaEntries(JsonUtil.createJson(getClass().getResourceAsStream("media-result-failed.json")));
	}
	
	@Test(expected=DecodingException.class)
	public void testParseNull() throws DecodingException {
		MediaDecoder decoder = new MediaDecoder();
		decoder.parseMediaEntries(null);
	}
	
}
