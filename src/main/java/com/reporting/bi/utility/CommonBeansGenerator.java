package com.reporting.bi.utility;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.reporting.bi.models.WhoDetailsModel;

@Component
public class CommonBeansGenerator {

	public WhoDetailsModel getCaller() {
		return new WhoDetailsModel("shantanu", "shantanu", LocalDateTime.now(), LocalDateTime.now(), true);
	}
}