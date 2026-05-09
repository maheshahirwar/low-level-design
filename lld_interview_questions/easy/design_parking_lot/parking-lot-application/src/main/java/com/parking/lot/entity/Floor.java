package com.parking.lot.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Floor {
	private Integer floorId;
	private List<String> spotIds;
}
