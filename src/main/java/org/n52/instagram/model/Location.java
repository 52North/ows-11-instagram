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
package org.n52.instagram.model;

import java.util.Map;

public class Location {

	private Double latitude;
	private Double longitude;
	private Integer id;
	private String name;

	private Location() {
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Location fromMap(Map<?, ?> map) {
		Location result = new Location();
		
		result.latitude = (Double) map.get("latitude");
		result.longitude = (Double) map.get("longitude");
		
		Object id = map.get("id");
		if (id != null) {
			result.id = (Integer) id;
		}
		
		Object name = map.get("name");
		if (name != null) {
			result.name = (String) name;
		}
		return result;
	}

}