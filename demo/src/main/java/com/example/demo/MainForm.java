package com.example.demo;

import lombok.Data;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.MainFormList;

@Data
public class MainForm {

	private List<MainFormList> mainFormList;

	private MultipartFile mainForm_file;

}
