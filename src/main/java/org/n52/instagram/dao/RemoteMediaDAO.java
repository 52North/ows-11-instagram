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
package org.n52.instagram.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.n52.instagram.decode.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteMediaDAO implements MediaDAO {
	
	private static final Logger logger = LoggerFactory
			.getLogger(RemoteMediaDAO.class);

	private String TARGET_URL_LAT_LON_SCHEME = "%s/media/search?lat=%s&lng=%s&access_token=%s";
	
	private String baseUrl;
	private String accessToken;

	public RemoteMediaDAO(String baseUrl, String accessToken) {
		this.baseUrl = baseUrl;
		this.accessToken = accessToken;
	}

	@Override
	public Map<?, ?> search(double latitude, double longitude) {
		HttpGet get = new HttpGet(String.format(TARGET_URL_LAT_LON_SCHEME,
				baseUrl, latitude, longitude, accessToken));
		try (CloseableHttpClient client = HttpClientBuilder.create().build();) {
			CloseableHttpResponse response = client.execute(get);
			
			if (response.getEntity() != null) {
				Map<?, ?> json = JsonUtil.createJson(response.getEntity().getContent());
				return json;
			}
			else {
				logger.warn("Could not retrieve contents. No entity.");
			}
		} catch (IOException e) {
			logger.warn("Could not retrieve contents of "+get.getURI(), e);
		}
		
		return null;
	}

}
