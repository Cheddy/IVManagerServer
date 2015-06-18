package net.cheddy.ivmanager.views;

import io.dropwizard.views.View;
import net.cheddy.ivmanager.model.Intervention;


public class CompactInterventionView extends View {
	private final Intervention iv;

	public CompactInterventionView(Intervention iv) {
		super("iv.ftl");
		this.iv = iv;
	}

	public Intervention getIv() {
		return iv;
	}
}