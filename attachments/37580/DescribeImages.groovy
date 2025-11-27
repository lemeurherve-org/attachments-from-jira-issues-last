
@Grab("com.amazonaws:aws-java-sdk:1.11.125")
import com.amazonaws.services.ec2.model.DescribeImagesRequest
import com.amazonaws.services.ec2.model.Filter
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain

class DescribeImages implements Serializable {

	static String DATE_FORMAT		= "yyyy-MM-dd'T'hh:mm:ss"

	String region;

	DescribeImages() {
		this('us-east-1')
	}

	DescribeImages(regionValue) {
		this.region = regionValue
	}

	String getLatestRets() {
		def filters = Arrays.asList(new Filter("tag:Name", Arrays.asList("Convert Rets Base Image")))
		return getLatest(filters)
	}

	String getLatestIntermediate(assetID, targetName) {
		def filters = Arrays.asList(
			new Filter("tag:AssetID", Arrays.asList(assetID))
			, new Filter("tag:TargetName", Arrays.asList(targetName))
			, new Filter("tag:AMIType", Arrays.asList("Intermediate"))
		)
		return getLatest(filters)
	}

	String getLatestComplete(releaseKey, assetID, targetName) {
		def filters = Arrays.asList(
			new Filter("tag:ReleaseKey", Arrays.asList(releaseKey))
			, new Filter("tag:AssetID", Arrays.asList(assetID))
			, new Filter("tag:TargetName", Arrays.asList(targetName))
			, new Filter("tag:AMIType", Arrays.asList("Complete"))
		)
		return getLatest(filters)
	}

	String getLatest(filters) {
		def test = new DescribeImagesRequest()
		test.setFilters(filters)

		def client = AmazonEC2ClientBuilder.standard()
						.withRegion(getRegion())
						.withCredentials(new ProfileCredentialsProvider("test"))
						.build()

		def response = client.describeImages(test)

		def image = response.getImages().max{it.getCreationDate()}

		return image.getImageId()
	}

	String getRegion() {
		return this.region
	}

 	void setRegion(value) {
		this.region = value
	}

}
