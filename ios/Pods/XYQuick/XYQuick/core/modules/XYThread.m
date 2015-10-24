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
//  This file Copy from Samurai.

#import "XYThread.h"

#pragma mark -

@interface XYGCD()

@property (nonatomic, strong) dispatch_queue_t foreQueue;
@property (nonatomic, strong) dispatch_queue_t backSerialQueue;
@property (nonatomic, strong) dispatch_queue_t backConcurrentQueue;
@property (nonatomic, strong) dispatch_queue_t writeFileQueue;

@end

@implementation XYGCD uxy_def_singleton

- (id)init
{
	self = [super init];
	if ( self )
	{
        _foreQueue           = dispatch_get_main_queue();
        _backSerialQueue     = dispatch_queue_create( "com.XY.backSerialQueue", DISPATCH_QUEUE_SERIAL );
        _backConcurrentQueue = dispatch_queue_create( "com.XY.backConcurrentQueue", DISPATCH_QUEUE_CONCURRENT );
        _writeFileQueue      = dispatch_queue_create( "com.XY.writeFileQueue", DISPATCH_QUEUE_SERIAL );
	}
	
	return self;
}

+ (dispatch_time_t)seconds:(CGFloat)f
{
    return dispatch_time( DISPATCH_TIME_NOW, f * 1ull * NSEC_PER_SEC );
}
@end


#pragma mark -
#if (1 == __XY_DEBUG_UNITTESTING__)
// ----------------------------------
// Unit test
// ----------------------------------
#import "XYUnitTest.h"

UXY_TEST_CASE( Core, XYThead )
{
    //	TODO( "test case" )
}

UXY_DESCRIBE( test1 )
{
    __block int step = 1;
    uxy_dispatch_after_background_concurrent(1)
    {
        step = 3;
    }
    uxy_dispatch_submit
    step = 2;
}

UXY_DESCRIBE( test2 )
{
    __block int step = 1;
    uxy_dispatch_background_concurrent
    {
        step = 3;
        uxy_dispatch_foreground
        {
            step = 4;
        }
        uxy_dispatch_submit
    }
    uxy_dispatch_submit
    step = 2;
}

UXY_DESCRIBE( test3 )
{
    __block int step = 1;
    
    for (int i = 1; i < 100; i++)
    {
        uxy_dispatch_background_concurrent
        {
            step = 3;
        }
        uxy_dispatch_submit
    }
    
    uxy_dispatch_barrier_background_concurrent
    {
        step = 4;
    }
    uxy_dispatch_submit
    // 这里的调度顺序, 有可能先执行到step3 然后才执行step2
    step = 2;
}

UXY_TEST_CASE_END
#endif




