
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.util.Streams;

/**
 *
 * @author dbligh
 */
public class ServletFileUploadXSS extends ServletFileUpload {

    public ServletFileUploadXSS() {
        super();
    }

    public ServletFileUploadXSS(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }

    @Override
    public List<FileItem> parseRequest(HttpServletRequest request) throws FileUploadException {
        List<FileItem> list = parseRequest(new ServletRequestContext(request));
        List<FileItem> cleanList = new ArrayList();
        for (FileItem item : list) {
                if( item.isFormField() ){
                    try{
                        System.out.println("Cleaning inputs on fileitem: " + item.getName());
                        /* call a function here to actually perform the cross site script cleansing to your needs,
                         * there is a good example you can use here:
                         * https://www.javacodegeeks.com/2012/07/anti-cross-site-scripting-xss-filter.html
                         */
                        String cleaned = YourAppSecurity.cleanXSS(item.getString());
                        InputStream stream = new ByteArrayInputStream(cleaned.getBytes(StandardCharsets.UTF_8));
                        FileItemFactory fac = getFileItemFactory();
                        FileItem fileItem = fac.createItem(item.getFieldName(), item.getContentType(),
                                                   item.isFormField(), item.getName());
                        cleanList.add(fileItem);
                        Streams.copy(stream, fileItem.getOutputStream(), true);
                        System.out.println("Cleansed input successfully");
                    }catch(IOException e){
                        System.out.println("Failed to cleanse inputs: " + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }else{
                    cleanList.add(item);
                }
            }

        return cleanList;
    }
}
