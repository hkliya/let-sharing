//
//  __  __          ____           _          _
//  \ \/ / /\_/\   /___ \  _   _  (_)   ___  | | __
//   \  /  \_ _/  //  / / | | | | | |  / __| | |/ /
//   /  \   / \  / \_/ /  | |_| | | | | (__  |   <
//  /_/\_\  \_/  \___,_\   \__,_| |_|  \___| |_|\_\
//
//  Copyright (C) Heaven.
//
//	https://github.com/uxyheaven/XYQuick
//
//	Permission is hereby granted, free of charge, to any person obtaining a copy
//	of this software and associated documentation files (the "Software"), to deal
//	in the Software without restriction, including without limitation the rights
//	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//	copies of the Software, and to permit persons to whom the Software is
//	furnished to do so, subject to the following conditions:
//
//	The above copyright notice and this permission notice shall be included in
//	all copies or substantial portions of the Software.
//
//	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//	THE SOFTWARE.
//

#import "NSData+XY.h"

@implementation NSData (XYExtension)

@dynamic uxy_SHA1String;
@dynamic uxy_SHA1Data;
- (NSString *)uxy_MD5String
{
    uint8_t	digest[CC_MD5_DIGEST_LENGTH + 1] = { 0 };
    
    CC_MD5( [self bytes], (CC_LONG)[self length], digest );
    
    char tmp[16] = { 0 };
    char hex[256] = { 0 };
    
    for ( CC_LONG i = 0; i < CC_MD5_DIGEST_LENGTH; ++i )
    {
        sprintf( tmp, "%02X", digest[i] );
        strcat( (char *)hex, tmp );
    }
    
    return [NSString stringWithUTF8String:(const char *)hex];
}

- (NSData *)uxy_MD5Data
{
    uint8_t	digest[CC_MD5_DIGEST_LENGTH + 1] = { 0 };
    
    CC_MD5( [self bytes], (CC_LONG)[self length], digest );
    
    return [NSData dataWithBytes:digest length:CC_MD5_DIGEST_LENGTH];
}

@dynamic uxy_MD5String;
@dynamic uxy_MD5Data;
- (NSString *)uxy_SHA1String
{
    uint8_t	digest[CC_SHA1_DIGEST_LENGTH + 1] = { 0 };
    
    CC_SHA1( self.bytes, (CC_LONG)self.length, digest );
    
    char tmp[16] = { 0 };
    char hex[256] = { 0 };
    
    for ( CC_LONG i = 0; i < CC_SHA1_DIGEST_LENGTH; ++i )
    {
        sprintf( tmp, "%02X", digest[i] );
        strcat( (char *)hex, tmp );
    }
    
    return [NSString stringWithUTF8String:(const char *)hex];
}

- (NSData *)uxy_SHA1Data
{
    uint8_t	digest[CC_SHA1_DIGEST_LENGTH + 1] = { 0 };
    
    CC_SHA1( self.bytes, (CC_LONG)self.length, digest );
    
    return [NSData dataWithBytes:digest length:CC_SHA1_DIGEST_LENGTH];
}

@dynamic uxy_BASE64Encrypted;
- (NSString *)uxy_BASE64Encrypted
{
    static char * __base64EncodingTable = (char *)"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    
    // copy from THREE20
    
    if ( 0 == [self length] )
    {
        return @"";
    }
    
    char * characters = (char *)malloc((([self length] + 2) / 3) * 4);
    if ( NULL == characters )
    {
        return nil;
    }
    
    NSUInteger length = 0;
    NSUInteger i = 0;
    
    while ( i < [self length] )
    {
        char	buffer[3] = { 0 };
        short	bufferLength = 0;
        
        while ( bufferLength < 3 && i < [self length] )
        {
            buffer[bufferLength++] = ((char *)[self bytes])[i++];
        }
        
        // Encode the bytes in the buffer to four characters,
        // including padding "=" characters if necessary.
        characters[length++] = __base64EncodingTable[(buffer[0] & 0xFC) >> 2];
        characters[length++] = __base64EncodingTable[((buffer[0] & 0x03) << 4) | ((buffer[1] & 0xF0) >> 4)];
        
        if ( bufferLength > 1 )
        {
            characters[length++] = __base64EncodingTable[((buffer[1] & 0x0F) << 2) | ((buffer[2] & 0xC0) >> 6)];
        }
        else
        {
            characters[length++] = '=';
        }
        
        if ( bufferLength > 2 )
        {
            characters[length++] = __base64EncodingTable[buffer[2] & 0x3F];
        }
        else
        {
            characters[length++] = '=';
        }
    }
    
    return [[NSString alloc] initWithBytesNoCopy:characters length:length encoding:NSASCIIStringEncoding freeWhenDone:YES];
}

@end
