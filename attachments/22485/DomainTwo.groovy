class DomainTwo
{
	UUID id
	String name
	
	static hasMany =
	[
		domainOnes: DomainOne
	]
	
	static mapping = 
	{
		id generator: 'uuid2', type: 'pg-uuid'
	}
}