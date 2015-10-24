//
//  SecondViewController.m
//  Hackathon
//
//  Created by XingYao on 15/10/24.
//  Copyright © 2015年 XingYao. All rights reserved.
//

#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    self.view.backgroundColor = [UIColor whiteColor];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark -
- (NSString *)tabImageName
{
    return @"2";
}

- (NSString *)tabTitle
{
    return @"发现";
}

@end
