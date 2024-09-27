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

import java.util.Map;

import org.n52.socialmedia.model.Location;

public class InstagramLocation implements Location {

	private Double latitude;
	private Double longitude;
	private String id;
	private String name;

	private InstagramLocation() {
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getId() {
		if (id == null || id.isEmpty() || id.equalsIgnoreCase("")) {
			return String.format("%s_place-id-not-set",hashCode());
		}
		return id;
	}

	public String getName() {
		if (name == null || name.isEmpty() || name.equalsIgnoreCase("")) {
			return "name-not-set";
		}
		return name;
	}

	public static InstagramLocation fromMap(Map<?, ?> map) {
		if (map == null) {
			return null;
		}
		
		InstagramLocation result = new InstagramLocation();
		
		result.latitude = (Double) map.get("latitude");
		result.longitude = (Double) map.get("longitude");
		
		Object id = map.get("id");
		if (id != null) {
			result.id = id.toString();
		}
		
		Object name = map.get("name");
		if (name != null) {
			result.name = (String) name;
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstagramLocation other = (InstagramLocation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append("InstagramLocation [latitude=").append(getLatitude())
			.append(", longitude=").append(getLongitude())
			.append(", id=").append(getId())
			.append(", name=").append(getName()).append("]")
			.toString();
	}
	
	
	
}
