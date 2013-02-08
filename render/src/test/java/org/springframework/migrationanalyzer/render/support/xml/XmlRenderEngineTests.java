/*
 * Copyright 2013 the original author or authors.
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

package org.springframework.migrationanalyzer.render.support.xml;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.Writer;

import org.junit.Test;
import org.springframework.migrationanalyzer.analyze.AnalysisResult;
import org.springframework.migrationanalyzer.render.support.ViewRenderer;

public final class XmlRenderEngineTests {

    private final ViewRenderer viewRenderer = mock(ViewRenderer.class);

    private final XmlFileSystemEntryRenderer fileSystemEntryRenderer = mock(XmlFileSystemEntryRenderer.class);

    private final XmlRenderEngine renderEngine = new XmlRenderEngine(this.fileSystemEntryRenderer, this.viewRenderer);

    @Test
    public void canRenderXml() {
        assertTrue(this.renderEngine.canRender("xml"));
        assertFalse(this.renderEngine.canRender("html"));
    }

    @Test
    public void render() {
        AnalysisResult analysisResult = mock(AnalysisResult.class);

        this.renderEngine.render(analysisResult, "build");

        verify(this.viewRenderer).renderViewWithEmptyModel(eq("xml-header"), any(Writer.class));
        verify(this.fileSystemEntryRenderer).renderFileSystemEntries(eq(analysisResult), any(Writer.class));
        verify(this.viewRenderer).renderViewWithEmptyModel(eq("xml-footer"), any(Writer.class));
    }

}
