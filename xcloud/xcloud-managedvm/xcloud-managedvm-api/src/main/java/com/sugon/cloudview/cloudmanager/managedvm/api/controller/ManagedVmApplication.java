package com.sugon.cloudview.cloudmanager.managedvm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sugon.cloudview.cloudmanager.managedvm.service.service.OldVirtualMachineService;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.sugon.cloudview.cloudmanager.**")
//@EnableJpaRepositories("com.sugon.cloudview.cloudmanager.**.repository")
//@EntityScan("com.sugon.cloudview.cloudmanager.**.entity")
//@RequestMapping("/managedvm")
public class ManagedVmApplication {

	private static Class<ManagedVmApplication> applicationClass = ManagedVmApplication.class;

	@Autowired
	private OldVirtualMachineService oldVirtualMachineService;

	@Transactional(readOnly = false)
	@RequestMapping("/synchronize")
	public void synchronize() {
		try {
			System.out.println("开始同步！！！");
			oldVirtualMachineService.syncVM();
			System.out.println("同步完成！！！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Transactional(readOnly = true)
	// @RequestMapping("/listPage")
	// public void listPage() {
	// org.springframework.data.domain.Pageable pageable = new PageRequest(0,
	// 10);
	// try {
	// Page<OldVirtualMachine> oPage =
	// oldVirtualMachineService.listByIsMandated(1, pageable);
	// Page<OldVirtualMachine> oPage1 =
	// oldVirtualMachineService.listByIsMandated(0, pageable);
	// System.out.println(oPage);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// @Transactional(readOnly = false)
	// @RequestMapping("/mandate")
	// public void mandate() {
	// try {
	// //
	// oldVirtualMachineService.mandate("8a8295dc53ab80780153ab83ed550000");//
	// // 测试托管已托管的
	// // oldVirtualMachineService.mandate("8a8295dc53ab80780150");//
	// // 测试不存在的虚拟机
	// //
	// oldVirtualMachineService.mandate("8a8295dc53ab80780153ab83ed550000");//
	// // 测试已被孤立的虚拟机
	// oldVirtualMachineService.mandate("8a8295dc53ab80780153ab83ed690003");//
	// 测试正常的虚拟机
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	@Transactional(readOnly = true)
	@RequestMapping("/start")
	public void start() {
		try {
			oldVirtualMachineService.start("8a8295dc53abb9850153abc1a7d90000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	@RequestMapping("/stop")
	public void stop() {
		try {
			oldVirtualMachineService.stop("8a8295dc53abb9850153abc1a7d90000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	@RequestMapping("/restart")
	public void restart() {
		try {
			oldVirtualMachineService.restart("8a8295dc53abb9850153abc1a7d90000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	@RequestMapping("/display")
	public void display() {
		try {
			oldVirtualMachineService.display("8a8295dc53abb9850153abc1a7d90000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(applicationClass, args);
	}
}
