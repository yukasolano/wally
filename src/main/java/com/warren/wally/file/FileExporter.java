package com.warren.wally.file;

import java.util.List;

public interface FileExporter<T> {

    ExportedFile export(List<T> dados);

}
