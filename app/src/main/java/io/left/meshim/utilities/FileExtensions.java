package io.left.meshim.utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Generic file extensions.
 */
public class FileExtensions {
    public static final  Set IMAGE = new HashSet<>(Arrays.asList("png", "jpg","svg"));
    public static final  String[] DOCUMENTS = new String[] {"txt","xlsx", "xls", "doc",
            "docx", "ppt", "pptx", "pdf","zip","gif"};
}
