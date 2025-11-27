REM ********************************************************************************
REM ************      Batch file to analyse IAR C-STAT html report      ************
REM ********************************************************************************
REM
REM Looks through top level c-stat html report for the High, Medium and Low warning count
REM This file will write messages to the console. It does not set the project to unstable, etc.
REM The CI tool needs to check the console for messages
REM e.g. Jenkins can use the 'Text Finder' plugin as a bost build action to look for .*Static Code Analysis FAILED.*
REM
REM Note: This is based on information from:
REM https://dzone.com/articles/integration-of-c-stat-code-analysis-with-automated
REM
REM Some values (such as workspace) are set by Jenkins
REM Other values must already have been set in the script calling this .bat file:
REM
REM These should be set before calling this script (example values shown):
REM set cstat_path=.\cstat_html
REM set cstat_results=cstat_result.html
REM
REM These are optional - defaults will be used if not set (example values shown). Note, if not a number then not used:
REM set lim_h=1
REM set lim_m=1
REM set lim_l=Off

REM Make sure starting in the right place, initialise local variables, delete previous results
cd %workspace%


echo Analysing top level C-STAT html report %cstat_results%
echo.

setlocal EnableDelayedExpansion

REM Default limits for High, Medium and Low warnings (if not set externally)
REM Use a nummber for limit value. Text (e.g. Off) will mean no limit check
set default_lim_h=1
set default_lim_m=1
set default_lim_l=1
if "%lim_h%"=="" (
    set lim_h=%default_lim_h%
    echo High limit was not set.   Using default value: %default_lim_h%
)
if "%lim_m%"=="" (
    set lim_m=%default_lim_m%
    echo Medium limit was not set. Using default value: %default_lim_m%
)
if "%lim_l%"=="" (
    set lim_l=%default_lim_l%
    echo Low limit was not set.    Using default value: %default_lim_l%
)

REM Find the High, Medium and Low labels
REM These may be in any order, but will be in the form:
REM     labels : ["Medium","Low","High"],
for /F "delims=" %%g in ('FINDSTR /r /c:"labels : " %cstat_path%\%cstat_results%') do set labels=%%g
set labels=%labels: =%
set labels=%labels:~8,-2%
for /f "tokens=1-3 delims=," %%a in ("%labels%") do (
    set labela=%%a
    set labelb=%%b
    set labelc=%%c
)
set labela=%labela:~1,-1%
set labelb=%labelb:~1,-1%
set labelc=%labelc:~1,-1%

REM Find the data values. These are in the form:
REM         data : [110,7,6]
for /F "delims=" %%a in ('FINDSTR /r /c:"data : [[0-9]*,[0-9]*,[0-9]*]" %cstat_path%\%cstat_results%') do set data=%%a
set data=%data: =%
set data=%data:~6,-1%

for /f "tokens=1-3 delims=," %%a in ("%data%") do (
    REM Match first label to first value
    if %labela%==High   (set high=%%a)
    if %labela%==Medium (set med=%%a)
    if %labela%==Low    (set low=%%a)
    REM Match second label to second value
    if %labelb%==High   (set high=%%b)
    if %labelb%==Medium (set med=%%b)
    if %labelb%==Low    (set low=%%b)
    REM Match third label to third value
    if %labelc%==High   (set high=%%c)
    if %labelc%==Medium (set med=%%c)
    if %labelc%==Low    (set low=%%c)
)

REM Check that High, Medium and Low labels have been found
set "no_label="
if not defined high (
    echo ERROR: High label not found
    set no_label=1
)
if not defined med (
    echo ERROR: Medium label not found
    set no_label=1
)
if not defined low (
    echo ERROR: Low label not found
    set no_label=1
)
if defined no_label goto label_error

echo -------------------------------------
echo Labels: %labels%
echo Data  : %data%
echo -------------------------------------
echo Message: Number (Limit)
echo High   : %high%      (%lim_h%)
echo Medium : %med%      (%lim_m%)
echo Low    : %low%      (%lim_l%)
echo -------------------------------------

set "fail="
if %high% GEQ %lim_h% (
    echo High message limit exceeded.
    set fail=1
)
if %med%  GEQ %lim_m% (
    echo Medium message limit exceeded.
    set fail=1
)
if %low%  GEQ %lim_l% (
    echo Low message limit exceeded.
    set fail=1
)
if defined fail goto error

echo.
echo Static Code Analysis Passed
echo.
echo -------------------------------------

goto end


:label_error
echo -------------------------------------------------------
echo.
echo Static Code Analysis FAILED
echo.
echo Expected label(s) not found in %cstat_results%
echo Labels: %labels%
echo Expecting "High", "Medium" and "Low" (in any order)
echo.
echo -------------------------------------------------------
goto end

:error
echo -------------------------------------------------------
echo.
echo Static Code Analysis FAILED
echo.
echo See C-STAT html report %cstat_results% for details
echo.
echo -------------------------------------------------------
goto end

:end
pause
