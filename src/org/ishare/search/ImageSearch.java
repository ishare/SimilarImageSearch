package org.ishare.search;

import java.awt.image.BufferedImage;

public class ImageSearch {
    
    /**
     * 计算"汉明距离"（Hamming distance）。 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
     * 
     * @param sourceHashCode 源hashCode
     * @param hashCode 与之比较的hashCode
     */
    public static int hammingDistance(String sourceHashCode, String hashCode) {
        int difference = 0;
        int len = sourceHashCode.length();

        for (int i = 0; i < len; i++) {
            if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
                difference++;
            }
        }

        return difference;
    }

    /**
     * 生成图片指纹
     * 
     * @param filename 文件名
     * @return 图片指纹
     */
    public static String produceFingerPrint(String filename) {
        System.out.println("fetch: " + filename);
        BufferedImage source = ImageHelper.readPNGImage(filename);// 读取文件

        if (source == null) {
            System.out.println("file cannot read: " + filename);
            return null;
        }

        int width = 8;
        int height = 8;

        // 缩小尺寸。
        // 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。
        BufferedImage thumb = ImageHelper.thumb(source, width, height, false);

        // 简化色彩。
        // 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
        int[] pixels = new int[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i * height + j] = ImageHelper.rgbToGray(thumb.getRGB(i, j));
            }
        }

        // DCT，取信息量大部分
        // DCT.dct(pixels); // 留信息量大的部分

        // 计算平均值。
        // 计算所有64个像素的灰度平均值。
        int avgPixel = ImageHelper.average(pixels);

        // 比较像素的灰度。
        // 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
        int[] comps = new int[width * height];
        for (int i = 0; i < comps.length; i++) {
            if (pixels[i] >= avgPixel) {
                comps[i] = 1;
            } else {
                comps[i] = 0;
            }
        }

        // 计算哈希值。
        // 将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
        StringBuffer hashCode = new StringBuffer();
        // for (int i = 0; i < comps.length; i += 4) {
        // int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2) + comps[i + 2]
        // * (int) Math.pow(2, 1) + comps[i + 2];
        // hashCode.append(binaryToHex(result));
        // }
        for (int i = 0; i < comps.length; i++) {
            hashCode.append(comps[i]);
        }

        // 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
        return hashCode.toString();
    }

    /**
     * 二进制转为十六进制
     * 
     * @param int binary
     * @return char hex
     */
    private static char binaryToHex(int binary) {
        char ch = ' ';
        switch (binary) {
        case 0:
            ch = '0';
            break;
        case 1:
            ch = '1';
            break;
        case 2:
            ch = '2';
            break;
        case 3:
            ch = '3';
            break;
        case 4:
            ch = '4';
            break;
        case 5:
            ch = '5';
            break;
        case 6:
            ch = '6';
            break;
        case 7:
            ch = '7';
            break;
        case 8:
            ch = '8';
            break;
        case 9:
            ch = '9';
            break;
        case 10:
            ch = 'a';
            break;
        case 11:
            ch = 'b';
            break;
        case 12:
            ch = 'c';
            break;
        case 13:
            ch = 'd';
            break;
        case 14:
            ch = 'e';
            break;
        case 15:
            ch = 'f';
            break;
        default:
            ch = ' ';
        }
        return ch;
    }

}
