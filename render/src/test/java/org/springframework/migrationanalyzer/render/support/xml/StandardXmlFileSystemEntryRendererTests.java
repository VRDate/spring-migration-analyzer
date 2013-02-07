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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.migrationanalyzer.analyze.AnalysisResult;
import org.springframework.migrationanalyzer.analyze.fs.FileSystemEntry;
import org.springframework.migrationanalyzer.render.ByFileSystemEntryController;
import org.springframework.migrationanalyzer.render.OutputPathGenerator;
import org.springframework.migrationanalyzer.render.support.ViewRenderer;
import org.springframework.migrationanalyzer.render.support.source.SourceAccessor;

@SuppressWarnings("rawtypes")
public final class StandardXmlFileSystemEntryRendererTests {

    private final Set<ByFileSystemEntryController> fileSystemEntryControllers = new HashSet<ByFileSystemEntryController>();

    private final SourceAccessor sourceAccessor = mock(SourceAccessor.class);

    private final ViewRenderer viewRenderer = mock(ViewRenderer.class);

    private final Writer writer = new StringWriter();

    private final StandardXmlFileSystemEntryRenderer renderer = new StandardXmlFileSystemEntryRenderer(this.fileSystemEntryControllers,
        this.sourceAccessor, this.viewRenderer);

    @SuppressWarnings("unchecked")
    @Test
    public void renderFileSystemEntries() {
        when(this.sourceAccessor.getSource(any(FileSystemEntry.class))).thenReturn("the-source");

        AnalysisResult analysisResult = mock(AnalysisResult.class);
        FileSystemEntry entry = mock(FileSystemEntry.class);
        when(analysisResult.getFileSystemEntries()).thenReturn(new HashSet<FileSystemEntry>(Arrays.asList(entry)));
        when(analysisResult.getResultForEntry(entry)).thenReturn(analysisResult);
        when(analysisResult.getResultTypes()).thenReturn(new HashSet<Class<?>>(Arrays.asList(Object.class)));

        this.renderer.renderFileSystemEntries(analysisResult, this.writer);

        InOrder inOrder = Mockito.inOrder(this.viewRenderer);

        inOrder.verify(this.viewRenderer).renderViewWithEmptyModel("xml-by-file-header", this.writer);
        inOrder.verify(this.viewRenderer).renderViewWithModel(eq("xml-by-file-entry-header"), anyMap(), eq(this.writer));
        inOrder.verify(this.viewRenderer).render(eq(Object.class), anySet(), eq(this.fileSystemEntryControllers), eq(this.writer),
            isNull(OutputPathGenerator.class), eq("xml"));
        inOrder.verify(this.viewRenderer).renderViewWithModel(eq("xml-by-file-entry-source"), anyMap(), eq(this.writer));
        inOrder.verify(this.viewRenderer).renderViewWithEmptyModel("xml-by-file-entry-footer", this.writer);
        inOrder.verify(this.viewRenderer).renderViewWithEmptyModel("xml-by-file-footer", this.writer);

        verifyNoMoreInteractions(this.viewRenderer);
    }

}
