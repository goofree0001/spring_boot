/*
https://qiita.com/Haru_3/items/eba8fdb296aa809e9546
https://learning-collection.com/springboot%e5%85%a5%e9%96%80/

https://qiita.com/k-tabuchi/items/81fa2c774f92c63125b5
Spring アップロードファイルを読み込んで中身を表示する方法

https://yossan.hatenablog.com/entry/2019/05/25/123853
InputStreamReader 例
OutputStreamWriter 例

https://qiita.com/riekure/items/31c1a9e371c9020a4973
ファイル書き込み機能を実装してみた



*/


package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional; 
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.BufferedReader;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.DemoService;
import com.example.demo.DemoEntity;
import com.example.demo.EditForm;
import com.example.demo.MainForm;
import com.example.demo.MainFormList;

@Controller
public class DemoController {

	DemoEntity temp_entity;
	Integer temp_pkid;

	@Autowired
	DemoService demoService;

	@RequestMapping(path = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String root() {
			return "redirect:/main";
	}

	@RequestMapping(path = "/{path}", method = {RequestMethod.GET, RequestMethod.POST})
	public String main(
	@PathVariable String path,
	@RequestParam(name = "mode", required = false) String mode,
	@RequestParam (name = "list_pkid", required = false)Integer list_pkid,
	MainForm mainForm,
	EditForm editForm,
	HttpServletResponse response
//	Model model
	) {

	System.out.println("path:" + path);
	System.out.println("mode:" + mode);

	if ("edit".equals(path) || "delete".equals(path) || "csv_download".equals(path)) {
//	switch (path) {
//	case "edit":
//	case "delete":
//	case "csv_download":
		if (mainForm.getMainFormList() == null) {
				System.out.println("MainFormList() == null");
				return "redirect:/main";
		}
	}

	switch (path) {
	case "main":
		List<DemoEntity> list_findAll = demoService.findAll();

		List<MainFormList> temp_mainFormList = new ArrayList<MainFormList>();
		list_findAll.forEach(list_detail -> {
			MainFormList list_temp = new MainFormList();
			list_temp.setEntity(list_detail);
			temp_mainFormList.add(list_temp);
		});

		mainForm.setMainFormList(temp_mainFormList);
		return "index.html";

	case "create":
		switch (mode) {
		case "create":
			editForm = new EditForm();
			temp_entity = new DemoEntity();
			editForm.setEntity(temp_entity);
			editForm.setPath(path);
			return "edit.html";
		case "regist":
			temp_entity = editForm.getEntity();
			demoService.create(temp_entity);
			return "redirect:/main";
		case "back":
			return "redirect:/main";
		}
		break;

	case "edit":
		System.out.println("case \"edit\" mode:" + mode);
		switch (mode) {
		case "regist":
			temp_entity = editForm.getEntity();
			demoService.update(temp_entity);
			return "redirect:/main";
		case "back":
			return "redirect:/main";
		}
		break;

	case "csv_upload":
		MultipartFile mp_file = mainForm.getMainForm_file();
		csv_upload(mp_file);
		return "redirect:/main";

	case "sub_win":
		System.out.println("list_pkid:" + list_pkid);
		temp_entity = demoService.findById(list_pkid);
		editForm.setEntity(temp_entity);
		editForm.setPath(path);
		return "edit.html";

	} // switch (path) {

	if ("edit".equals(path) || "delete".equals(path) || "csv_download".equals(path)) {
//	switch (path) {
//	case "edit":
//	case "delete":
//	case "csv_download":
		System.out.println("switch (path) edit,delete,csv_download in");
		String str = "";
		temp_pkid = 0;
		for (MainFormList mainFormList : mainForm.getMainFormList()) {
			if (mainFormList.getSelectCheckboxs().length > 0) {
				temp_pkid = mainFormList.getSelectCheckboxs()[0];
				switch (path) {
				case "edit":
					switch (mode) {
					case "edit":
						temp_entity = demoService.findById(temp_pkid);
						editForm.setEntity(temp_entity);
						editForm.setPath(path);
						return "edit.html";
					}
					break;
				case "delete":
					demoService.delete(temp_pkid);
					break;
				case "csv_download":
					temp_entity = demoService.findById(temp_pkid);
					str = str +
					temp_entity.getPkid() + "," +
					"\"" + temp_entity.getName() + "\"" + "," +
					temp_entity.getWins() + "," +
					temp_entity.getBest() + "," +
					temp_entity.getSize() + "\r\n";
					break;
				} // switch (path) {
			} // if (mainFormList.getSelectCheckboxs().length > 0) {
		} // for (MainFormList mainFormList : mainForm.getMainFormList()) {
		if (temp_pkid == 0) {
			System.out.println("no checkbox");
			return "redirect:/main";
		}

		switch (path) {
		case "csv_download":
			csv_download(response,str);
			return "/main";
		}

		return "redirect:/main";
	} // if ("edit".equals(path) || "delete".equals(path) || "csv_download".equals(path)) {

	System.out.println("no catch");
	return "redirect:/main";

	} // public String main() {

	void csv_download(HttpServletResponse response ,String str) {
		try{

		byte[] buff = str.getBytes();

		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition","attachment; filename=list.csv");
		response.setContentLength(buff.length);

		 ServletOutputStream os = response.getOutputStream();
		 os.write(buff);
		 os.close();
		}
		catch(IOException e){
		 e.printStackTrace();
		} // try{


	} // csv_download() {

	void csv_upload(MultipartFile mp_file) {

		List<String> lines = new ArrayList<String>();
		String line = null;
		String[] line_split = null;
		temp_entity = new DemoEntity();

		try {
			InputStream stream = mp_file.getInputStream();
			Reader reader = new InputStreamReader(stream);
			BufferedReader buf= new BufferedReader(reader);
			while((line = buf.readLine()) != null) {
				if (line.equals("")) {
					continue;
				}

				line_split = line.split(",");
				for (int i = 0;i < line_split.length;i++) {
					line_split[i] = line_split[i].replace("\"","");
					line_split[i] = line_split[i].replace("'","");
				}

				temp_entity.setName(line_split[1]);
				temp_entity.setWins(Integer.parseInt(line_split[2]));
				temp_entity.setBest(Integer.parseInt(line_split[3]));
				temp_entity.setSize(Float.parseFloat(line_split[4]));

				try {
					temp_pkid = Integer.parseInt(line_split[0]);
				}
				catch(NumberFormatException e) {
					System.out.println("Cannot parse the string to integer:" + line_split[0]);
					return;
				}

				if (temp_pkid == 0) {
					demoService.create(temp_entity);
					System.out.println("create finished");
				} else {
					temp_entity.setPkid(temp_pkid);
					demoService.update(temp_entity);
					System.out.println("update finished");
				}
			} // while((line = buf.readLine()) != null) {
			line = buf.readLine();

		} catch (IOException e) {
			System.out.println("Can't read contents.");
			e.printStackTrace();
		} // try {

	} // csv_upload() {

} // public class DemoController {

