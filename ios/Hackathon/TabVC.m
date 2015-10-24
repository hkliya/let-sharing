//
//  TabVC.m
//  Hackathon
//
//  Created by XingYao on 15/10/24.
//  Copyright © 2015年 XingYao. All rights reserved.
//

#import "TabVC.h"
#import "FirstViewController.h"
#import "SecondViewController.h"
#import "GroupVC.h"
#import "MineVC.h"

@interface TabVC ()

@end

@implementation TabVC

+(instancetype)tabBar
{
    TabVC *tvc = [[TabVC alloc] initWithTabBarHeight:(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) ? 70 : 50];
    [tvc setMinimumHeightToDisplayTitle:40.0];
    tvc.iconGlossyIsHidden = NO;
    tvc.navigationController.hidesBottomBarWhenPushed = YES;
    
    tvc.backgroundImageName = @"tabbar_bg.png";
    tvc.selectedBackgroundImageName = @"tabbar_bg.png";
    
    tvc.tabColors = @[[UIColor whiteColor],
                                    [UIColor whiteColor],
                                    [UIColor whiteColor],
                                    [UIColor whiteColor]];
    tvc.selectedTabColors = @[[UIColor whiteColor],
                                            [UIColor whiteColor],
                                            [UIColor whiteColor],
                                            [UIColor whiteColor]];
    
    tvc.iconColors = @[[UIColor whiteColor],
                                     [UIColor whiteColor],
                                     [UIColor whiteColor],
                                     [UIColor whiteColor]];
    
    tvc.selectedIconColors = @[[UIColor blueColor],
                                             [UIColor blueColor],
                                             [UIColor blueColor],
                                             [UIColor blueColor]];
    tvc.tabStrokeColor = [UIColor clearColor];
    tvc.tabEdgeColor = [UIColor clearColor];
    
    tvc.textColor = [UIColor blackColor];
    tvc.selectedTextColor = [UIColor blackColor];
    
    FirstViewController *vc1 = [[FirstViewController alloc] init];
    [tvc setViewControllers:[NSMutableArray arrayWithObjects:
                                           vc1,
                                           [[SecondViewController alloc] init],
                                           [[GroupVC alloc] init],
                                           [[MineVC alloc] init],
                                           nil]];
    
    
    return tvc;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
