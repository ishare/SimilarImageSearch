# SimilarImageSearch
Similar Image Search based on [Dr. Neal Krawetz's Algorithm](http://www.hackerfactor.com/blog/index.php?/archives/432-Looks-Like-It.html)

## Steps:
*Reduce size. 
**The fastest way to remove high frequencies and detail is to shrink the image. In this case, shrink it to 8x8 so that there are 64 total pixels. Don't bother keeping the aspect ratio, just crush it down to fit an 8x8 square. This way, the hash will match any variation of the image, regardless of scale or aspect ratio.
*Reduce color.
**The tiny 8x8 picture is converted to a grayscale. This changes the hash from 64 pixels (64 red, 64 green, and 64 blue) to 64 total colors.
*Average the colors.
**Compute the mean value of the 64 colors.
*Compute the bits.
**This is the fun part. Each bit is simply set based on whether the color value is above or below the mean.
Construct the hash. Set the 64 bits into a 64-bit integer. The order does not matter, just as long as you are consistent. (I set the bits from left to right, top to bottom using big-endian.)
