package matlabjenkins.matlabjnk;

import java.io.IOException;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.TopLevelItem;
import hudson.model.TopLevelItemDescriptor;
import hudson.model.AbstractItem;
import hudson.model.Job;
import hudson.model.Project;
import jenkins.model.Jenkins;

@SuppressWarnings("unchecked")
public class MatlabItemProject extends Project<MatlabItemProject, MatlabItemBuild> implements TopLevelItem {

	

	@SuppressWarnings("rawtypes")
	public MatlabItemProject(ItemGroup parent, String name)  {
		
		super(parent, name);
		
				

	}
	public MatlabItemProject(Jenkins parent, String name) {
        super(parent, name);
    }


	



	@Override
	public TopLevelItemDescriptor getDescriptor() {

		return (TopLevelItemDescriptor)Jenkins.getInstance().getDescriptorOrDie(getClass());
	}

	@Override
	protected Class<MatlabItemBuild> getBuildClass() {

		return MatlabItemBuild.class;
	}



	@Extension
	public static final class DescriptorImpl extends TopLevelItemDescriptor/*AbstractProjectDescriptor*/ {
		@Override
		public String getDisplayName() {
			return "MATLAB Runner";
		}
		
		
		public DescriptorImpl() {
			super();
		}
	   		


		@Override
		public MatlabItemProject newInstance(@SuppressWarnings("rawtypes") ItemGroup parent, String name) {
			return new MatlabItemProject(parent,name);
		}
	}

	



}
