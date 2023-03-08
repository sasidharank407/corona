package com.example.demo.controller;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServices;
import com.example.demo.services.CoronaVirusServiceDataRepository;

@Controller

public class HomeController 
{
	
	CoronaVirusDataServices crnService;
	
	@Autowired
	CoronaVirusServiceDataRepository repository;
	
	@Autowired
	public void setCrnService(CoronaVirusDataServices crnService) {
		this.crnService = crnService;
	}


	@GetMapping("/")
	public String home(Model model)
	{
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return "home";
	}
	
	
	@RequestMapping(path="/collectChartData",produces= {"application/json"})
	@ResponseBody
	public List<LocationStates>collectChartData(Model model)
	{
		System.out.println("Here View Chart Data....");
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return  allstates;
		
	}
	@RequestMapping(path="/collectChartData/{id}",produces= {"application/json"})
	@ResponseBody
	public Optional<LocationStates>collectChartDataByCountryID(@PathVariable("id") int countryid ,Model model)
	{
		System.out.println("Here View Chart Data By COuntryID...");
		Optional<LocationStates> locationStates=repository.findById(countryid);
		return locationStates;
		
	}
	
	@RequestMapping(path="/collectChartData/country={name}",produces= {"application/json"})
	@ResponseBody
	public LocationStates collectChartDataByCountryName(@PathVariable("name") String countryName ,Model model)
	{
		System.out.println("Here View Chart Data By COuntryID...");
		LocationStates locationStates=repository.findByCountry(countryName);
		return locationStates;
		
		
	}
	@RequestMapping(value="viewChart",method=RequestMethod.GET)
	public ModelAndView viewChart()
	
	{
		return new ModelAndView("ViewChart").addObject("myURL",new String("http://localhost:8081/collectChartData"));
		
	}
	
	@GetMapping("/viewChart/{id}")
	public String viewChartByID(@PathVariable("id")int id,Model m )
	{
		m.addAttribute("id",id);
		m.addAttribute("myURL","http://localhost:8081/collectChartData"+id);
		return "ViewChart";
		
	}
	@GetMapping(path="/collectChartData/country={name}")
	public String viewChartByCountryName(@RequestParam String name,Model m)
	{
		m.addAttribute("myURL","http://localhost:8081/collectChartData"+name);
		
		return "ViewChart";
		
	}

}
