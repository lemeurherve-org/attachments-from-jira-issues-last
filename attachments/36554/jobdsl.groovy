job("SampleJob") {
  parameters {

    choiceParam('ENV', ["Test1","Test2","Test3","Test4"], '')


activeChoiceReactiveParam('Field01') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField01')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field02') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField02')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field03') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField03')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field04') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField04')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field05') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField05')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field06') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField06')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field07') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField07')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field08') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField08')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field09') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField09')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field10') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField10')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field11') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField11')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field12') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField12')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field13') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField13')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field14') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField14')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field15') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField15')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field16') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField16')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field17') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField17')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field18') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField18')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field19') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField19')
  }
  referencedParameter('ENV')
}

    activeChoiceReactiveParam('Field20') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField20')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field21') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField21')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field22') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField22')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field23') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField23')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field24') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField24')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field25') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField25')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field26') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField26')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field27') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField27')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field28') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField28')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field29') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField29')
  }
  referencedParameter('ENV')
}
activeChoiceReactiveParam('Field30') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField30')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field31') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField31')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field32') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField32')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field33') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField33')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field34') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField34')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field35') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField35')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field36') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField36')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field37') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField37')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field38') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField38')
  }
  referencedParameter('ENV')
}

activeChoiceReactiveParam('Field39') {
 description('')
  choiceType('SINGLE_SELECT')
  scriptlerScript('EnvValues.groovy') {
    parameter('file', 'c:\\test.json')
    parameter('lookupKey', 'env')
    parameter('lookupValue', '$ENV')
    parameter('key', 'ValueOfField39')
  }
  referencedParameter('ENV')
}


  }



}
