package cn.choleece.bing.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;

public class QRCodeUtil {

    public static void main(String[] args) {
        QrCodeUtil.generate("http://hutool.cn/", 300, 300, FileUtil.file("/Users/sf/Desktop/qrcode/qrcode.jpg"));
    }
}
