package com.reporting.bi.models;

import java.util.UUID;

import com.reporting.bi.entity.TemplateRegister;

import jakarta.annotation.Nullable;

public record TemplateRegisterModel(
	@Nullable UUID templateId,
	String name,
	@Nullable String path,
	@Nullable String extension) {
	
}