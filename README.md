# ServletFileUploadXSS
Provides some Cross Site Scripting security for the ServletFileUpload class

When dealing with file upload forms, you would typically use code that would look something like this:

    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
    for (FileItem item : items) {
        //do something
    }
  
# Usage

To use this class, simply call the wrapper class for ServletFileUpload:

    List<FileItem> items = new ServletFileUploadXSS(new DiskFileItemFactory()).parseRequest(request);
    for (FileItem item : items) {
        //do something
    }
  
This is usually easier to do when just using Parameters, but to be sure about protecting your text inputs for mulitpart forms, this class can come in handy.

# Important

This extension class just provides an avenue for performing XSS protection. I haven't included the actual scripting which does this cleaning. I have made a recommendation to use something like this:

https://www.javacodegeeks.com/2012/07/anti-cross-site-scripting-xss-filter.html

I'll leave it up to you to decide how aggressively you want to implement XSS protection.
