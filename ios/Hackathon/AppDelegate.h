//
//  AppDelegate.h
//  Hackathon
//
//  Created by XingYao on 15/10/24.
//  Copyright © 2015年 XingYao. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "TabVC.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (nonatomic, strong) TabVC *tabBarController;

@end


@interface UIViewController (AKTabBarController)
@property (nonatomic, weak, readonly) TabVC *tvc;
@end