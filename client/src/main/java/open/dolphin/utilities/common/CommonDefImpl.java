/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package open.dolphin.utilities.common;

/**
 * 共通定義インターフェース
 * @author S.Oh@Life Sciences Computing Corporation.
 */
public interface CommonDefImpl {
    // Dicom
    String FILEFMT_BMP = "bmp";
    String FILEFMT_JPG = "jpg";
    String FILEFMT_PNG = "png";
    String FILEFMT_GIF = "gif";
    String FILEFMT_DCM = "dcm";
    int FILEHEADERSIZE  = 14;
    int INFOHEADERSIZE  = 40;
    int HEADERSIZE = FILEHEADERSIZE + INFOHEADERSIZE;
    short UL = 0x554c;
    short OB = 0x4f42;
    short OW = 0x4f57;
    short UN = 0x554e;
    short SQ = 0x5351;

    // Http
    String CONTENTTYPE_HTML = "text/html";
    String CONTENTTYPE_XML = "text/xml";
    String CONTENTTYPE_TEXT = "text/plain";
    String CONTENTTYPE_GIF = "text/gif";
    String CONTENTTYPE_JPEG = "text/jpeg";
    String CONTENTTYPE_MPEG = "text/mpeg";
    String REQUESTMETHOD_GET = "GET";
    String REQUESTMETHOD_POST = "POST";
    String PROTOCOL_HTTP = "http";
    String CHARSET_DEFAULT = "UTF-8";
    
    // SocketConnect
    String CHARSET_SHIFTJIS = "Shift_JIS";
}

