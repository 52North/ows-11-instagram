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
 * icense version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.instagram;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.n52.instagram.dao.MediaDAO;
import org.n52.instagram.dao.RemoteMediaDAO;
import org.n52.instagram.decode.DecodingException;
import org.n52.instagram.decode.MediaDecoder;
import org.n52.instagram.model.PostedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstagramCrawler {

	private static final Logger logger = LoggerFactory.getLogger(InstagramCrawler.class);
	
	private static final String INSTAGRAM_CREDENTIALS_PROPERTIES = "/instagram_credentials.properties";
	private String accessToken;
	private String baseUrl;

	public InstagramCrawler() {
		loadProperties();
		loadDaoImplementations();
	}

	private void loadDaoImplementations() {
		
	}

	private void loadProperties() {
		InputStream is = getClass().getResourceAsStream(INSTAGRAM_CREDENTIALS_PROPERTIES);
		if (is == null) {
			throw new IllegalStateException(INSTAGRAM_CREDENTIALS_PROPERTIES + " file not found.");
		}
		
		Properties props = new Properties();
		try {
			props.load(is);
			this.accessToken = props.getProperty("ACCESS_TOKEN");
			this.baseUrl = props.getProperty("BASE_URL");
		} catch (IOException e) {
			logger.warn("properties malformed or unreadable", e);
			throw new IllegalStateException(e);
		}
		
	}
	
	public List<PostedImage> searchForImagesAt(double latitude, double longitude) throws DecodingException {
		List<PostedImage> result = new ArrayList<>();
		
		MediaDAO dao = new RemoteMediaDAO(baseUrl, accessToken);
		MediaDecoder decoder = new MediaDecoder();
		
		result = decoder.parseMediaEntries(dao.search(latitude, longitude));
		return result;
	}
	
	public static void main(String[] args) throws DecodingException {
		new InstagramCrawler().searchForImagesAt(51.930077892, 7.625061267);
	}
	
}
