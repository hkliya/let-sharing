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

#import "XYQuick_Predefine.h"
#pragma mark -

typedef void (^XYAnimateStepBlock)(void);


@interface XYAnimate : NSObject

@end
//////////////////////          XYAnimateStep        ///////////////////////


@interface XYAnimateStep : NSObject

+ (id)duration:(NSTimeInterval)duration
       animate:(XYAnimateStepBlock)step;

+ (id)delay:(NSTimeInterval)delay
   duration:(NSTimeInterval)duration
    animate:(XYAnimateStepBlock)step;

+ (id)delay:(NSTimeInterval)delay
   duration:(NSTimeInterval)duration
    option:(UIViewAnimationOptions)option
    animate:(XYAnimateStepBlock)step;

@property (nonatomic, assign) NSTimeInterval         delay;
@property (nonatomic, assign) NSTimeInterval         duration;
@property (nonatomic, copy  ) XYAnimateStepBlock     step;
@property (nonatomic, assign) UIViewAnimationOptions option;


- (void)runAnimated:(BOOL)animated;
- (void)run;

@end
//////////////////////          XYAnimateSerialStep        ///////////////////////
// 串行 序列  Serial Sequence
@interface XYAnimateSerialStep : XYAnimateStep
@property (nonatomic, strong, readonly) NSArray* steps;

+ (id)animate;
- (id)addStep:(XYAnimateStep *)aStep;

@end
//////////////////////          XYAnimateParallelStep        ///////////////////////
// 并行 序列 Parallel Spawn
@interface XYAnimateParallelStep : XYAnimateStep

@property (nonatomic, strong, readonly) NSArray* steps;

+ (id)animate;
- (id)addStep:(XYAnimateStep *)aStep;

@end












