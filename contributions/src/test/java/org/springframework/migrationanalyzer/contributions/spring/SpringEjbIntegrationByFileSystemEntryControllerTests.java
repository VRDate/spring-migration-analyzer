/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.migrationanalyzer.contributions.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;
import org.springframework.migrationanalyzer.analyze.AnalysisResultEntry;
import org.springframework.migrationanalyzer.render.ModelAndView;

public class SpringEjbIntegrationByFileSystemEntryControllerTests {

    private final SpringEjbIntegrationByFileSystemEntryController controller = new SpringEjbIntegrationByFileSystemEntryController();

    @Test
    public void canHandle() {
        assertFalse(this.controller.canHandle(Object.class));
        assertTrue(this.controller.canHandle(SpringEjbIntegration.class));
    }

    @Test
    public void viewNameIsSet() {
        ModelAndView modelAndView = this.controller.handle(Collections.<AnalysisResultEntry<SpringEjbIntegration>> emptySet());
        assertEquals("spring-ejb-integration-by-file", modelAndView.getViewName());
    }

    @Test
    public void modelIsEmpty() {
        ModelAndView modelAndView = this.controller.handle(Collections.<AnalysisResultEntry<SpringEjbIntegration>> emptySet());
        assertNotNull(modelAndView.getModel());
        assertTrue(modelAndView.getModel().isEmpty());
    }
}
