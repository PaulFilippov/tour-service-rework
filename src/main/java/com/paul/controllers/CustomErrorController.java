package com.paul.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController{


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        String mes = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("mes", mes);
        model.addAttribute("statusCode", status);
        model.addAttribute("exception", exception);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "errors/error-404";
    }

//    @RequestMapping("/error")
//    @ResponseBody
//    public String handleError(HttpServletRequest request, ModelMap model) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
//        return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
//                        + "<div>Exception Message: <b>%s</b></div><body></html>",
//                statusCode, exception==null? "N/A": exception.getMessage());
////        model.addAttribute("statusCode", statusCode);
////        model.addAttribute("exception", exception);
////        return "errors/error-404";
//    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}