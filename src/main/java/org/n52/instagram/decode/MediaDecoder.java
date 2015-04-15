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
package org.n52.instagram.decode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.n52.instagram.model.PostedImage;
import org.n52.socialmedia.DecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaDecoder {
	
	private static final Logger logger = LoggerFactory
			.getLogger(MediaDecoder.class);

	
	public List<PostedImage> parseMediaEntries(Map<?, ?> json) throws DecodingException {
		return parseMediaEntries(json, null);
	}
	
	public List<PostedImage> parseMediaEntries(Map<?, ?> json, Filter filter) throws DecodingException {
		if (json == null) {
			throw new DecodingException("Cannot parse null object");
		}
		
		assertMeta(json.get("meta"));
		
		List<PostedImage> result = parseData(json.get("data"), filter);
		return result;
	}

	private List<PostedImage> parseData(Object object, Filter filter) throws DecodingException {
		if (!(object instanceof List<?>)) {
			throw new DecodingException("Could not process contents of data element: "+ object);
		}
		
		List<?> data = (List<?>) object;
		
		List<PostedImage> result = new ArrayList<>();
		PostedImage candidate;
		for (Object d : data) {
			if (d instanceof Map<?, ?>) {
				try {
					candidate = PostedImage.fromMap((Map<?, ?>) d);
					if (filter == null || filter.accepts(candidate)) {
						result.add(candidate);
					}
				}
				catch (DecodingException e) {
					logger.info("Could not parse data instance. "+ e.getMessage());
				}
			}
			else {
				logger.warn("Could not process element: "+d);
			}
		}
		
		return result;
	}

	private void assertMeta(Object object) throws DecodingException {
		if (!(object instanceof Map<?, ?>)) {
			throw new DecodingException("Could not process contents of meta element: "+ object);
		}
		
		Map<?, ?> meta = (Map<?, ?>) object;
		Object code = meta.get("code");
		if (!(code instanceof Integer)) {
			throw new DecodingException("Could not process contents of meta code element: "+ code);
		}
		
		Integer codeInt = (Integer) code;
		
		if (codeInt != 200) {
			throw new DecodingException("Unsupported HTTP Status: "+ codeInt);
		}
	}

}
