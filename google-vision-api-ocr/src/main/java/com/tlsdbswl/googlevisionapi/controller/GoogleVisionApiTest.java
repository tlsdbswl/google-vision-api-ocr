package com.tlsdbswl.googlevisionapi.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

@Controller
public class GoogleVisionApiTest {

	private final static Logger logger = LoggerFactory.getLogger(GoogleVisionApiTest.class);

	
	@RequestMapping(value = "/main.do")
	public ModelAndView main(ModelAndView mav) {
		
		mav.setViewName("/main");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/google-vision/file-upload-form.do")
	public ModelAndView imgText(ModelAndView mav) {
		String result = "";
		String imageFilePath = "C:\\Users\\shinyoonji\\Desktop\\img.jpg"; // 이미지 경로 설정
		try {
			List<AnnotateImageRequest> requests = new ArrayList<>();

			ByteString imgBytes = ByteString.readFrom(new FileInputStream(imageFilePath));

			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				List<AnnotateImageResponse> responses = response.getResponsesList();

				for (AnnotateImageResponse res : responses) {
					if (res.hasError()) {
						System.out.printf("Error: %s\n", res.getError().getMessage());
//						return mav;
					}
					result = res.getTextAnnotationsList().get(0).getDescription();
					System.out.println(result);
					
					mav.addObject("result", result);
					mav.setViewName("/google-vision/fileUploadForm");

					// For full list of available annotations, see http://g.co/cloud/vision/docs
					/*
					 * for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
					 * 
					 * //System.out.printf("Text: %s\n", annotation.getDescription());
					 * //System.out.printf("Position : %s\n", annotation.getBoundingPoly()); }
					 */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}