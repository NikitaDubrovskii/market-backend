package dev.dubrovsky.marketbackend.util;

import org.springframework.stereotype.Component;

@Component
public class ImageUtils {

/*    public static byte[] compressImageToBase64(byte[] imageBytes) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", outputStream);

        // Compress the image
        BufferedImage compressedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        compressedImage.createGraphics().drawImage(originalImage, 0, 0, originalImage.getWidth(), originalImage.getHeight(), null);
        ImageIO.write(compressedImage, "jpg", outputStream);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray()).getBytes();
    }

    public static byte[] decompressBase64ToImage(byte[] base64Image) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);

        return outputStream.toByteArray();
    }*/
}
