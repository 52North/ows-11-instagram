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

import java.util.Map;

import org.joda.time.DateTime;

public class RemoteMediaDAO extends AbstractRemoteDAO implements MediaDAO {
	

	private String LAT_LON_SCHEME = "%s/media/search?lat=%s&lng=%s&access_token=%s";
	private String LAT_LON_DIST_SCHEME = "%s/media/search?lat=%s&lng=%s&distance=%s&access_token=%s";
	
	private String LAT_LON_DIST_FROM_TO_SCHEME = "%s/media/search?lat=%s&lng=%s&distance=%s&min_timestamp=%s&max_timestamp=%s&access_token=%s";
	private String LAT_LON_DIST_FROM_SCHEME = "%s/media/search?lat=%s&lng=%s&distance=%s&min_timestamp=%s&access_token=%s";
	private String LAT_LON_DIST_TO_SCHEME = "%s/media/search?lat=%s&lng=%s&distance=%s&max_timestamp=%s&access_token=%s";
	
	private String LAT_LON_FROM_TO_SCHEME = "%s/media/search?lat=%s&lng=%s&min_timestamp=%s&max_timestamp=%s&access_token=%s";
	private String LAT_LON_FROM_SCHEME = "%s/media/search?lat=%s&lng=%s&min_timestamp=%s&access_token=%s";
	private String LAT_LON_TO_SCHEME = "%s/media/search?lat=%s&lng=%s&max_timestamp=%s&access_token=%s";
	
	public RemoteMediaDAO(String baseUrl, String accessToken) {
		super(baseUrl, accessToken);
	}

	@Override
	public Map<?, ?> search(double latitude, double longitude) {
		return executeApiRequest(String.format(LAT_LON_SCHEME,
				baseUrl, latitude, longitude, accessToken));
	}

	@Override
	public Map<?, ?> search(double latitude, double longitude,
			int distanceMeters) {
		return executeApiRequest(String.format(LAT_LON_DIST_SCHEME,
				baseUrl, latitude, longitude, distanceMeters, accessToken));
	}

	@Override
	public Map<?, ?> search(double latitude, double longitude,
			DateTime fromDate, DateTime toDate) {
		if (fromDate != null && toDate != null) {
			return executeApiRequest(String.format(LAT_LON_FROM_TO_SCHEME,
					baseUrl, latitude, longitude, fromDate.toDate().getTime(), toDate.toDate().getTime(), accessToken));
		}
		else if (fromDate == null && toDate != null) {
			return executeApiRequest(String.format(LAT_LON_TO_SCHEME,
					baseUrl, latitude, longitude, toDate.toDate().getTime(), accessToken));
		}
		else {
			return executeApiRequest(String.format(LAT_LON_FROM_SCHEME,
					baseUrl, latitude, longitude, fromDate.toDate().getTime(), accessToken));
		}
		
	}

	@Override
	public Map<?, ?> search(double latitude, double longitude,
			int distanceMeters, DateTime fromDate, DateTime toDate) {
		if (fromDate != null && toDate != null) {
			return executeApiRequest(String.format(LAT_LON_DIST_FROM_TO_SCHEME,
					baseUrl, latitude, longitude, distanceMeters, fromDate.toDate().getTime()/1000, toDate.toDate().getTime()/1000, accessToken));
		}
		else if (fromDate == null && toDate != null) {
			return executeApiRequest(String.format(LAT_LON_DIST_TO_SCHEME,
					baseUrl, latitude, longitude, distanceMeters, toDate.toDate().getTime(), accessToken));
		}
		else {
			return executeApiRequest(String.format(LAT_LON_DIST_FROM_SCHEME,
					baseUrl, latitude, longitude, distanceMeters, fromDate.toDate().getTime(), accessToken));
		}
	}


}
