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
package org.n52.instagram.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.n52.socialmedia.DecodingException;
import org.n52.socialmedia.model.HumanVisualPerceptionObservation;
import org.n52.socialmedia.model.Procedure;
import org.n52.socialmedia.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostedImage implements HumanVisualPerceptionObservation {
	
	private static final Logger logger = LoggerFactory
			.getLogger(PostedImage.class);

	private String id;
	private Procedure procedure;
	private InstagramLocation location;
	private List<String> tags;
	private String caption;
	private DateTime createdTime;
	private String imageUrl;
	private String link;
	
	public InstagramLocation getLocation() {
		return location;
	}

	public List<String> getTags() {
		return tags;
	}
	
	public String getCaption() {
		return caption;
	}

	public DateTime getCreatedTime() {
		return createdTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getLink() {
		return link;
	}

	private PostedImage() {
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof PostedImage)) {
			return false;
		}
		
		PostedImage that = (PostedImage) obj;
		
		return that.id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	public static PostedImage fromMap(Map<?, ?> d) throws DecodingException {
		Object type = d.get("type");
		if (!type.equals("image")) {
			throw new DecodingException("Not of type image! type="+type);
		}
		
		PostedImage result = new PostedImage();
		result.id = (String) d.get("id");
		
		User user = User.fromMap((Map<?, ?>) d.get("user"));
		result.procedure = new Procedure(user.getName(), user.getInstagramURL());
		result.location = InstagramLocation.fromMap((Map<?, ?>) d.get("location"));
		
		result.tags = parseTags(d.get("tags"));
		result.caption = parseCaption(d.get("caption"));
		result.imageUrl = parseImageUrl(d.get("images"));
		result.createdTime = parseCreatedTime(d.get("created_time"));
		result.link = (String) d.get("link");
		
		return result;
	}

	private static DateTime parseCreatedTime(Object object) {
		try {
			long ts = Long.parseLong(object.toString());
			DateTime result = new DateTime(ts*1000);
			return result;
		}
		catch (IllegalArgumentException e) {
			logger.warn("Could not parse time: "+ object);
		}
		return null;
	}

	private static String parseImageUrl(Object object) {
		if (object instanceof Map<?, ?>) {
			Object standard = ((Map<?, ?>) object).get("standard_resolution");
			if (standard instanceof Map<?, ?>) {
				return (String) ((Map<?, ?>) standard).get("url");
			}
		}
		return null;
	}

	private static String parseCaption(Object object) {
		if (object instanceof Map<?, ?>) {
			String text = (String) ((Map<?, ?>) object).get("text");
			return text.trim();
		}
		return null;
	}

	private static List<String> parseTags(Object object) {
		List<String> result = new ArrayList<>();
		
		if (object instanceof List<?>) {
			for (Object string : (List<?>) object) {
				String tag = (String) string;
				tag = tag.trim();
				if (!tag.isEmpty()) {
					result.add(tag);
				}
			}
		}
		
		return result;
	}

	@Override
	public DateTime getPhenomenonTime() {
		return this.createdTime;
	}

	@Override
	public DateTime getResultTime() {
		return this.createdTime;
	}

	@Override
	public String getResult() {
		String result = null;
		if (caption == null || caption.isEmpty()) {
			result = StringUtil.createTagList(tags, " ");
		} else if (tags.size() > 0) {
			result = String.format("%s; Tags: %s", caption,
					StringUtil.createTagList(tags, " "));
		}
		
		if (result == null) {
			result = caption;
		}
		
		return String.format("%s; URL: %s", result, imageUrl);
	}

	@Override
	public String getIdentifier() {
		return id;
	}

	@Override
	public Procedure getProcedure() {
		return this.procedure;
	}

	@Override
	public String getResultHref() {
		return link;
	}

}
