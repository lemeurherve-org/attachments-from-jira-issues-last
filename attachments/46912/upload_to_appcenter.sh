# script based on example from https://docs.microsoft.com/en-us/appcenter/distribution/uploading
filename="$1"
apitoken="$2"
owner="$3"
appname="$4"
releasenotes="$5"
destination="$6"

CURL="curl"
JQ_BIN="<path_to_jq>\jq"

# Check if file exists
if [ ! -f $filename ]; then
    echo "File '$filename' not found!"
	exit 1
fi

# Request for an upload resource on server
uploadprep_result=$($CURL -s -X POST --header "Content-Type: application/json" --header "Accept: application/json" --header "X-API-Token: $apitoken" https://api.appcenter.ms/v0.1/apps/$owner/$appname/release_uploads)

# Fetch upload id and upload url from responce
upload_id=$(echo $uploadprep_result | $JQ_BIN -r ".upload_id")
upload_url=$(echo $uploadprep_result | $JQ_BIN -r ".upload_url")

if [ "$upload_id" == "null" -o -z "$upload_id" ]; then
	echo "request failed to get upload url"
	exit 1
fi

# upload file
echo "upload file"
$CURL -F "ipa=@${filename}" ${upload_url}

res=$?
if test "$res" != "0"; then
  echo "upload failed, curl exit code: $res"
  exit 1
fi

# mark file as uploaded (aka committed)
echo "mark upload as committed"
commit_result=$($CURL -s -X PATCH --header 'Content-Type: application/json' --header 'Accept: application/json' --header "X-API-Token: $apitoken" -d '{ "status": "committed" }' https://api.appcenter.ms/v0.1/apps/$owner/$appname/release_uploads/$upload_id)

#echo "mark upload as committed: $commit_result"
release_id=$(echo $commit_result | $JQ_BIN -r ".release_id")
release_url=$(echo $commit_result | $JQ_BIN -r ".release_url")

res=$?
if [ "$release_id" == "null" -o -z "$release_id" ]; then
	echo "request failed to commit upload: $res"
	exit 1
fi

# Set release notes and assign to distributions group
echo "release version"
release_result=$($CURL -s -X PATCH --header 'Content-Type: application/json' --header 'Accept: application/json' --header "X-API-Token: $apitoken" -d "{ \"destination_name\": \"$destination\", \"release_notes\": \"$releasenotes\" }" https://api.appcenter.ms/$release_url)

#echo "release version: $release_result"

checkresult=$(echo $release_result | $JQ_BIN -r ".release_notes")
if [ "$checkresult" == "null" -o -z "$checkresult" ]; then
	echo "request faild to release version"
	exit 1
fi

echo "upload success"
