package com.juubes.nexus.data;

import java.util.LinkedHashSet;

import com.juubes.nexus.NexusLocation;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AbstractMap {
	@NonNull
	public final String id;

	@NonNull
	public String displayName;

	@NonNull
	public NexusLocation lobby;

	public int ticks;

	public final LinkedHashSet<? extends AbstractTeam> teams = new LinkedHashSet<>();

}
