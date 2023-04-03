package com.reporting.bi.models;

import java.util.UUID;

import com.reporting.bi.entity.QueryRegister;
import com.reporting.bi.entity.ReportRegister;
import com.reporting.bi.entity.TemplateRegister;

import jakarta.validation.constraints.NotNull;

public record ReportRegisterModel(@NotNull UUID queryId,@NotNull UUID templateId,@NotNull String name) {
}
