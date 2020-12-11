package com.innosen.fileDownload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/filedownload")
public class FileDownload extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("filename");
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("/image/" + filename);
        FileInputStream fis = new FileInputStream(realPath);

        String mimeType = context.getMimeType(filename);
        resp.setHeader("content-type",mimeType);
        /*文件下载时以附件形式打开*/
        resp.setHeader("content-disposition","attachment;filename=" + filename);

        ServletOutputStream os = resp.getOutputStream();
        byte[] buff = new byte[1024 * 8];
        int len;
        while ((len = fis.read(buff)) != -1) {
            os.write(buff,0,len);
        }

        fis.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
